package bearmaps.proj2c.server.handler.impl;

public class ImageSet {
    private final double left_lon;
    private final double right_lon;
    private final double top_lat;
    private final double bottom_lat;
    private final double width_lon;
    private final double height_lat;
    private final int MIN_DEPTH = 0;
    private final int MAX_DEPTH = 7;


    public ImageSet(double left_lon, double right_lon, double top_lat, double bottom_lat, int depth) {
        this.left_lon = left_lon;
        this.right_lon = right_lon;
        this.top_lat = top_lat;
        this.bottom_lat = bottom_lat;
        width_lon = right_lon - left_lon;
        height_lat = bottom_lat - top_lat;
    }

    public boolean range_intersect_imageset(double range_left_lon, double range_right_lon, double range_top_lat, double range_bottom_lat) {
        // TODO - implement
        return true;
    }

    public boolean point_in_ImageSet(double lat, double lon) {
        return bottom_lat <= lat & lat <= top_lat & left_lon <= lon & lon <= right_lon;
    }

    public double min_depth() {
        return MIN_DEPTH;
    }

    public double max_depth() {
        return MAX_DEPTH;
    }

    public double left_lon() {
        return left_lon;
    }

    public double right_lon() {
        return right_lon;
    }

    public double top_lat() {
        return top_lat;
    }

    public double bottom_lat() {
        return bottom_lat;
    }

    public double width_lon() {
        return width_lon;
    }

    public double height_lat() {
        return height_lat;
    }
}
