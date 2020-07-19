package com.wordpress.abhirockzz.javaee8.builderWithoutBoilerPlateSet;


public class SimpleBean {
    private final int index;
    private final String name;

    public SimpleBean(final int index, final String name) {
        this.index = index;
        this.name = name;
    }

    public int index() {
        return index;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "index=" + index +
                ", name='" + name + '\'' +
                '}';
    }

    public static class SimpleBeanBuilder {


//        public SimpleBeanBuilder1 setIndex(int index) {
//            return name -> () -> new SimpleBean(index, name);
//        }

        public static SimpleBeanBuilder0 builder() {
            return index -> //usa index della setIndex(index)
                    name -> //usa name della setName(name)
                            () -> new SimpleBean(index, name);// build()
        }

        public interface SimpleBeanBuilder0 {
            SimpleBeanBuilder1 setIndex(final int index);
        }

        public interface SimpleBeanBuilder1 {
            SimpleBeanBuilder2 setName(final String name);
        }

        public interface SimpleBeanBuilder2 {
            SimpleBean build();
        }

//        public SimpleBeanBuilder1 setIndex(int index) {
//            return name -> () -> new SimpleBean(index, name);
//        }


        //OLD jdk7:
//        public static SimpleBeanBuilder builder() {
//            return new SimpleBeanBuilder();
//        }
//
//        private static class SimpleBeanBuilder {
//            public SimpleBeanBuilder1 setIndex(int index) {
//                return new SimpleBeanBuilder1() {
//                    @Override
//                    public SimpleBeanBuilder2 setName(final String name) {
//                        return new SimpleBeanBuilder2() {
//                            @Override
//                            public SimpleBean build() {
//                                return new SimpleBean(index, name);
//                            }
//                        };
//                    }
//                };
//            }
//            public interface SimpleBeanBuilder1 {
//                SimpleBeanBuilder2 setName(final String name);
//            }
//            public interface SimpleBeanBuilder2 {
//                SimpleBean build();
//            }
//        }

    }


}