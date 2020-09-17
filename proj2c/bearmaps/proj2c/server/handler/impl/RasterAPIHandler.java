package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import bearmaps.proj2c.utils.Constants;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;
import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 *
 * @author rahul, Josh Hug, Brian Elinsky
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};
    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};
    private final ImageSet imageSet = new ImageSet(-122.29980468, -122.21191406, 37.89219554, 37.82280243, 7);
    private final int IMG_WIDTH_PIXELS = 256;

    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *                      the user viewport width and height.
     * @param response      : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     * can also be interpreted as the length of the numbers in the image
     * string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
//        System.out.println("yo, wanna know the parameters given by the web browser? They are:");
//        System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        if (!valid_request(requestParams)) {
            return queryFail();
        }
        // If (!imageset.intersect_imageset(requestParams)
        //       return queryFail();
        //

        int depth = optimal_image_depth(requestParams.get("lrlon"), requestParams.get("ullon"), requestParams.get("w"), imageSet);
        Image top_left_image = top_left_image(requestParams.get("ullon"), requestParams.get("ullat"), depth, imageSet);
        Image bottom_right_image = bottom_right_image(requestParams.get("lrlon"), requestParams.get("lrlat"), depth, imageSet);

        results.put("render_grid", generate_grid(top_left_image, bottom_right_image));
        results.put("raster_ul_lon", top_left_image.ullon());
        results.put("raster_ul_lat", top_left_image.ullat());
        results.put("raster_lr_lon", bottom_right_image.lrlon());
        results.put("raster_lr_lat", bottom_right_image.lrlat());
        results.put("depth", depth);
        results.put("query_success", true);

        return results;
    }

    private Image top_left_image(double ullon, double ullat, int depth, ImageSet imageSet) {
        ullon = Math.max(ullon, imageSet.left_lon());
        ullat = Math.min(ullat, imageSet.top_lat());
        return new Image(ullon, ullat, depth, imageSet);
    }

    private Image bottom_right_image(double lrlon, double lrlat, int depth, ImageSet imageSet) {
        lrlon = Math.min(lrlon, imageSet.right_lon());
        lrlat = Math.max(lrlat, imageSet.bottom_lat());
        return new Image(lrlon, lrlat, depth, imageSet);
    }

    private boolean valid_request(Map<String, Double> requestParams) {
        if (requestParams.get("ullon") >= requestParams.get("lrlon")) {
            return false;
        } else if (requestParams.get("ullat") <= requestParams.get("lrlat")) {
            return false;
        }
        return true;
    }

    /**
     * This method takes as input images in the top left corner and bottom right corner of the query box.  It returns
     * a 2-dimensional array of image names that fill the whole query box.
     *
     * @param top_left     : String, the name of the image in the top left of the query box.
     * @param bottom_right : String, the name of the image in the bottom right of the query box.
     * @return : String[][] : 2-dimensional array of images that fill up the whole query box.
     */
    private String[][] generate_grid(Image top_left, Image bottom_right) {
        int depth = top_left.depth();
        int grid_width = bottom_right.x_coord() - top_left.x_coord() + 1;
        int grid_height = bottom_right.y_coord() - top_left.y_coord() + 1;
        String[][] render_grid = new String[grid_height][grid_width];
        for (int x = 0; x < grid_width; x++) {
            for (int y = 0; y < grid_height; y++) {
                render_grid[y][x] = String.format("d%d_x%d_y%d.png", depth, x + top_left.x_coord(), y + top_left.y_coord());
                // TODO - make this a function.  It is now in two places in your class.
            }
        }
        return render_grid;
    }

    /**
     * Takes the width and the left and right longitudes of the query box and returns the optimal depth for the grid of
     * images.
     *
     * @param right_lon Number, the bounding lower right longitude of the query box.
     * @param left_lon  Number, the bounding upper left longitude of the query box.
     * @param w         Number, the width, in pixels, of the query box.
     * @return A integer representing the depth for the display files.
     */
    private int optimal_image_depth(double right_lon, double left_lon, double w, ImageSet imageSet) {
        double Query_Box_LongDPP = (right_lon - left_lon) / w;
        int optimal_image_depth = 0;
        double Rastered_Image_LongDPP = (imageSet.right_lon() - imageSet.left_lon()) / IMG_WIDTH_PIXELS;
        while (Rastered_Image_LongDPP >= Query_Box_LongDPP & optimal_image_depth < imageSet.max_depth()) {
            optimal_image_depth++;
            Rastered_Image_LongDPP = Rastered_Image_LongDPP / 2.0;
        }
        return optimal_image_depth;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     *
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                           ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
