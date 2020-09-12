package bearmaps.proj2c.server.handler.impl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image {

    private final double REGION_ULLON = -122.29980468;
    private final double REGION_LRLON = -122.21191406;
    private final double REGION_ULLAT = 37.89219554;
    private final double REGION_LRLAT = 37.82280243;
    private final double REGION_WIDTH_LON = REGION_LRLON - REGION_ULLON;
    private final double REGION_HEIGHT_LAT = REGION_LRLAT - REGION_ULLAT;

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
    private final int MIN_DEPTH = 0;
    private final int MAX_DEPTH = 7;

    public Image(String img_name) {
        name = img_name;
        x_coord = get_img_x_coord(img_name);
        y_coord = get_img_y_coord(img_name);
        depth = Integer.parseInt(img_name.substring(1, 2));
        img_width_lon = img_width_in_lon_at_depth(depth);
        img_height_lat = img_height_in_lat_at_depth(depth);
        ullon = REGION_ULLON + (x_coord * img_width_lon);
        ullat = REGION_ULLAT + (y_coord * img_height_lat);
        lrlon = REGION_ULLON + ((x_coord + 1) * img_width_lon);
        lrlat = REGION_ULLAT + ((y_coord + 1) * img_height_lat);
    }

    public Image(double lon, double lat, int d) {
        depth = d;
        img_width_lon = img_width_in_lon_at_depth(depth);
        img_height_lat = img_height_in_lat_at_depth(depth);
        x_coord = (int)((lon - REGION_ULLON) / img_width_lon);
        y_coord = (int)((lat - REGION_ULLAT) / img_height_lat);
        name = String.format("d%d_x%d_y%d.png", depth, x_coord, y_coord);
        ullon = REGION_ULLON + (x_coord * img_width_lon);
        ullat = REGION_ULLAT + (y_coord * img_height_lat);
        lrlon = REGION_ULLON + ((x_coord + 1) * img_width_lon);
        lrlat = REGION_ULLAT + ((y_coord + 1) * img_height_lat);
    }

    public int get_depth() {
        return depth;
    }

    public String get_name() {
        return name;
    }

    public double get_image_width_lon() {
        return img_width_lon;
    }

    public double get_image_height_lat() {
        return img_height_lat;
    }


    public double get_ullon() {
        return ullon;
    }

    public double get_ullat() {
        return ullat;
    }

    public double get_lrlon() {
        return lrlon;
    }

    public double get_lrlat() {
        return lrlat;
    }

    public int get_x_coord() {
        return x_coord;
    }

    public int get_y_coord() {
        return y_coord;
    }

    private int get_img_x_coord(String img_name) {
        Pattern pattern = Pattern.compile("(?<=_x)[0-9]+");
        Matcher m = pattern.matcher(img_name);
        m.find();
        return Integer.parseInt(m.group());
    }

    private int get_img_y_coord(String img_name) {
        Pattern pattern = Pattern.compile("(?<=_y)[0-9]+");
        Matcher m = pattern.matcher(img_name);
        m.find();
        return Integer.parseInt(m.group());
    }

    private double img_height_in_lat_at_depth(int depth) {
        return REGION_HEIGHT_LAT / Math.pow(2, depth);
    }

    private double img_width_in_lon_at_depth(int depth) {
        return REGION_WIDTH_LON / Math.pow(2, depth);
    }


}
