package kz.kamadi.yandextranslate.domain.entity;

import java.util.List;

public class DictionaryEntity {
    private List<Def> def;
    private List<Def> defs;
    private List<Link> link;

    public List<Link> getLink() {
        return this.link;
    }

    public void setLink(List<Link> list) {
        this.link = list;
    }

    public List<Def> getDef() {
        return this.def;
    }

    public void setDef(List<Def> list) {
        this.def = list;
    }

    public List<Def> getDefs() {
        return this.defs;
    }

    public void setDefs(List<Def> list) {
        this.defs = list;
    }

    public class Attribute {
        private String text;

        public String getText() {
            return this.text;
        }

        public void setText(String str) {
            this.text = str;
        }
    }

    public class TextItem {
        protected Attribute gen;
        protected Attribute pos;
        protected String text;

        public TextItem() {
            this.pos = new Attribute();
            this.gen = new Attribute();
        }

        public TextItem(TextItem textItem) {
            this.pos = new Attribute();
            this.gen = new Attribute();
            this.text = textItem.getText();
            this.pos = textItem.getPos();
            this.gen = textItem.getGen();
        }

        public String getText() {
            return this.text;
        }

        public void setText(String str) {
            this.text = str;
        }

        public Attribute getPos() {
            return this.pos;
        }

        public void setPos(Attribute attribute) {
            this.pos = attribute;
        }

        public Attribute getGen() {
            return this.gen;
        }

        public void setGen(Attribute attribute) {
            this.gen = attribute;
        }
    }

    public class Def extends TextItem {
        private List<Tr> tr;
        private String ts;

        public Def(TextItem textItem) {
            super(textItem);
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

    public class Link {
        private String href;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getHref() {
            return this.href;
        }

        public void setHref(String str) {
            this.href = str;
        }
    }

    public class Tr extends TextItem {
        private List<Def> ex;
        private List<TextItem> mean;
        private List<TextItem> syn;

        public Tr(TextItem textItem) {
            super(textItem);
        }

        public List<TextItem> getSyn() {
            return this.syn;
        }

        public void setSyn(List<TextItem> list) {
            this.syn = list;
        }

        public List<TextItem> getMean() {
            return this.mean;
        }

        public void setMean(List<TextItem> list) {
            this.mean = list;
        }

        public List<Def> getEx() {
            return this.ex;
        }

        public void setEx(List<Def> list) {
            this.ex = list;
        }
    }

}
