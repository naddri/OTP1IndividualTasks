public record Unit(int id, String key, String category, String name, String description) {
    @Override
    public String toString() {
        return name;
    }
}
