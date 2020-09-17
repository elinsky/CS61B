package bearmaps.proj2c.server.handler.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image {

    private final String name;
    private final double ullon;
    private final double ullat;
    private final double lrlon;
    private final double lrlat;
    private final int x_coord;
    private final int y_coord;
    private final double img_width_lon;
    private final double img_height_lat;
    private final int depth;
    private final int IMG_WIDTH_PIXELS = 256;
    private final int IMG_HEIGHT_PIXELS = 256;

    public Image(String img_name, ImageSet imageSet) {
        name = img_name;
        x_coord = get_img_x_coord(img_name);
        y_coord = get_img_y_coord(img_name);
        depth = Integer.parseInt(img_name.substring(1, 2));
        img_width_lon = img_width_in_lon_at_depth(depth, imageSet);
        img_height_lat = img_height_in_lat_at_depth(depth, imageSet);
        ullon = imageSet.left_lon() + (x_coord * img_width_lon);
        ullat = imageSet.top_lat() + (y_coord * img_height_lat);
        lrlon = imageSet.left_lon() + ((x_coord + 1) * img_width_lon);
        lrlat = imageSet.top_lat() + ((y_coord + 1) * img_height_lat);
    }

    public Image(double lon, double lat, int depth, ImageSet imageSet) {
        this.depth = depth;
        img_width_lon = img_width_in_lon_at_depth(depth, imageSet);
        img_height_lat = img_height_in_lat_at_depth(depth, imageSet);
        x_coord = (int)((lon - imageSet.left_lon()) / img_width_lon);
        y_coord = (int)((lat - imageSet.top_lat()) / img_height_lat);
        name = String.format("d%d_x%d_y%d.png", depth, x_coord, y_coord);
        ullon = imageSet.left_lon() + (x_coord * img_width_lon);
        ullat = imageSet.top_lat() + (y_coord * img_height_lat);
        lrlon = imageSet.left_lon() + ((x_coord + 1) * img_width_lon);
        lrlat = imageSet.top_lat() + ((y_coord + 1) * img_height_lat);
    }

    private boolean image_in_imageset(int depth, double ullon, double ullat, double lrlon, double lrlat, ImageSet imageSet) {
        if (depth < imageSet.min_depth() || depth > imageSet.max_depth()) {
            return false;
        } else if (ullon < imageSet.left_lon() || lrlon > imageSet.right_lon()) {
            return false;
        } else if (ullat > imageSet.top_lat() || lrlat < imageSet.bottom_lat()) {
            return false;
        } else {
            return true;
        }
    }

    public int depth() {
        return depth;
    }

    public String name() {
        return name;
    }

    public double image_width_lon() {
        return img_width_lon;
    }

    public double image_height_lat() {
        return img_height_lat;
    }


    public double ullon() {
        return ullon;
    }

    public double ullat() {
        return ullat;
    }

    public double lrlon() {
        return lrlon;
    }

    public double lrlat() {
        return lrlat;
    }

    public int x_coord() {
        return x_coord;
    }

    public int y_coord() {
        return y_coord;
    }

    private int get_img_x_coord(String img_name) {
        Pattern pattern = Pattern.compile("(?<=_x)[0-9]+");
        Matcher m = pattern.matcher(img_name);
        m.find();
        return Integer.parseInt(m.group());
    }

    private int get_img_y_coord(String img_name) {
        Pattern pattern = Pattern.compile("(?<=_y)[0m-9]+");
        Matcher m = pattern.matcher(img_name);
        m.find();
        return Integer.parseInt(m.group());
    }

    private double img_height_in_lat_at_depth(int depth, ImageSet imageSet) {
        return imageSet.height_lat() / Math.pow(2, depth);
    }

    private double img_width_in_lon_at_depth(int depth, ImageSet imageSet) {
        return imageSet.width_lon() / Math.pow(2, depth);
    }


}
