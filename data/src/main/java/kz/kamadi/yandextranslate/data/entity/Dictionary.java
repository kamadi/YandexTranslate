package kz.kamadi.yandextranslate.data.entity;

import java.util.List;

public class Dictionary {

    private List<Def> def;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Def> getDef() {
        return def;
    }

    public void setDef(List<Def> def) {
        this.def = def;
    }

    public static class Item {
        private String gen;
        private String pos;
        private String text;

        public Item() {

        }

        public Item(String gen, String pos, String text) {
            this.gen = gen;
            this.pos = pos;
            this.text = text;
        }

        public Item(Item item) {

            this.text = item.getText();
            this.pos = item.getPos();
            this.gen = item.getGen();
        }

        public String getGen() {
            return gen;
        }

        public void setGen(String gen) {
            this.gen = gen;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Def extends Item {
        private List<Tr> tr;
        private String ts;

        public Def() {

        }

        public Def(Item item) {
            super(item);
        }

        public List<Tr> getTr() {
            return this.tr;
        }

        public void setTr(List<Tr> list) {
            this.tr = list;
        }

        public String getTs() {
            return this.ts;
        }

        public void setTs(String str) {
            this.ts = str;
        }
    }

    public static class Tr extends Item {
        private List<Def> ex;
        private List<Item> mean;
        private List<Item> syn;
        private String asp;

        public Tr() {
        }

        public Tr(Item item) {
            super(item);
        }

        public List<Item> getSyn() {
            return this.syn;
        }

        public void setSyn(List<Item> list) {
            this.syn = list;
        }

        public List<Item> getMean() {
            return this.mean;
        }

        public void setMean(List<Item> list) {
            this.mean = list;
        }

        public List<Def> getEx() {
            return this.ex;
        }

        public void setEx(List<Def> list) {
            this.ex = list;
        }

        public String getAsp() {
            return asp;
        }

        public void setAsp(String asp) {
            this.asp = asp;
        }
    }
}
