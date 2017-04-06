package kz.kamadi.yandextranslate.domain.entity;

import java.util.List;

public class DictionaryEntity {

    private List<Def> def = null;

    public List<Def> getDef() {
        return def;
    }

    public void setDef(List<Def> def) {
        this.def = def;
    }

    public class Def {

        private String text;

        private String pos;

        private String ts;

        private List<Tr> tr = null;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public List<Tr> getTr() {
            return tr;
        }

        public void setTr(List<Tr> tr) {
            this.tr = tr;
        }

    }

    public class Tr {

        private String text;

        private String pos;

        private String gen;

        private List<Syn> syn = null;

        private List<Mean> mean = null;

        private List<Ex> ex = null;

        private String asp;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getGen() {
            return gen;
        }

        public void setGen(String gen) {
            this.gen = gen;
        }

        public List<Syn> getSyn() {
            return syn;
        }

        public void setSyn(List<Syn> syn) {
            this.syn = syn;
        }

        public List<Mean> getMean() {
            return mean;
        }

        public void setMean(List<Mean> mean) {
            this.mean = mean;
        }

        public List<Ex> getEx() {
            return ex;
        }

        public void setEx(List<Ex> ex) {
            this.ex = ex;
        }

        public String getAsp() {
            return asp;
        }

        public void setAsp(String asp) {
            this.asp = asp;
        }

    }

    public class Syn {

        private String text;

        private String pos;

        private String gen;

        private String asp;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getGen() {
            return gen;
        }

        public void setGen(String gen) {
            this.gen = gen;
        }

        public String getAsp() {
            return asp;
        }

        public void setAsp(String asp) {
            this.asp = asp;
        }

    }

    public class Mean {

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    public class Ex {

        private String text;

        private List<Tr> tr = null;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Tr> getTr() {
            return tr;
        }

        public void setTr(List<Tr> tr) {
            this.tr = tr;
        }

    }
}
