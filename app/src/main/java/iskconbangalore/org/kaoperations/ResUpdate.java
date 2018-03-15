package iskconbangalore.org.kaoperations;

/**
 * Created by i308830 on 2/25/18.
 */

public class ResUpdate {
    public String residency, eveningDel,eveningFeedback,morningDel,morningFeedback;


    ResUpdate() {
        this.eveningDel = "";
        this.eveningFeedback = "";
        this.morningDel="";
        this.morningFeedback="";
        this.residency="";

    }

    ResUpdate(String Residency, String MorningDel,String MorningFeedback,String EveningDel,String EveningFeedback) {
        this.eveningDel = Residency;
        this.eveningFeedback = EveningFeedback;
        this.morningDel=MorningDel;
        this.morningFeedback=MorningFeedback;
        this.residency=Residency;

    }

//    public class PrasadamUpdate {

    public String getResidency() {
        return residency;
    }

    public String getEveningDel() {
        return eveningDel;
    }

    public String getEveningFeedback() {
        return eveningFeedback;
    }

    public String getMorningDel() {
        return morningDel;
    }

    public String getMorningFeedback() {
        return morningFeedback;
    }

//
//        String time;
//        String feedback;
//    }
}

