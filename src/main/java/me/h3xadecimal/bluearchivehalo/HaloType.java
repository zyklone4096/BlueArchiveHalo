package me.h3xadecimal.bluearchivehalo;

public enum HaloType {
    None("", false),
    Alis("alis", false),
    Arona("arona", true),
    Azusa("azusa", false),
    Cherino("cherino", false),
    Hare("hare", false),
    Hoshino("hoshino", true),
    Koharu("koharu", false),
    Miku("miku", false),
    Miyu("miyu", false),
    Shiroko("shiroko", true),
    Yuuka("yuuka", false),
    Yuzu("yuzu", false);

    private final String resource;
    private final Double size;
    private final boolean reverse;

    HaloType(String resource, Double size, boolean reverse) {
        this.resource = resource;
        this.size = size;
        this.reverse = reverse;
    }

    HaloType(String resource, boolean reverse) {
        this(resource, 0.5, reverse);
    }

    public String getResource() {
        return resource;
    }

    public Double getSize() {
        return size;
    }

    public boolean isReverse() {
        return reverse;
    }
}
