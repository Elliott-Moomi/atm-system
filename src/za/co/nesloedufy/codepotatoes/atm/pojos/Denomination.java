package za.co.nesloedufy.codepotatoes.atm.pojos;

public class Denomination {
    private int twoHundredNotes;

    private int oneHundredNotes;

    private int fiftyRandNotes;

    private int twentyRandsNotes;

    private int tenRandsNotes;

    private int fiveRandCoin;

    private int twoRandCoin;

    private int oneRandCoin;

    public Denomination(){

    }
    public int getTwoHundredNotes() {
        return twoHundredNotes;
    }

    public int getOneHundredNotes() {
        return oneHundredNotes;
    }

    public int getFiftyRandNotes() {
        return fiftyRandNotes;
    }

    public int getTwentyRandsNotes() {
        return twentyRandsNotes;
    }

    public int getTenRandsNotes() {
        return tenRandsNotes;
    }

    public int getFiveRandCoin() {
        return fiveRandCoin;
    }

    public int getTwoRandCoin() {
        return twoRandCoin;
    }

    public int getOneRandCoin() {
        return oneRandCoin;
    }

    //----------------setters--------------------------
    public void setTwoHundredNotes(int twoHundredNotes) {
        this.twoHundredNotes = twoHundredNotes;
    }

    public void setOneHundredNotes(int oneHundredNotes) {
        this.oneHundredNotes = oneHundredNotes;
    }

    public void setFiftyRandNotes(int fiftyRandNotes) {
        this.fiftyRandNotes = fiftyRandNotes;
    }

    public void setTwentyRandsNotes(int twentyRandsNotes) {
        this.twentyRandsNotes = twentyRandsNotes;
    }

    public void setTenRandsNotes(int tenRandsNotes) {
        this.tenRandsNotes = tenRandsNotes;
    }

    public void setFiveRandCoin(int fiveRandCoin) {
        this.fiveRandCoin = fiveRandCoin;
    }

    public void setTwoRandCoin(int twoRandCoin) {
        this.twoRandCoin = twoRandCoin;
    }

    public void setOneRandCoin(int oneRandCoin) {
        this.oneRandCoin = oneRandCoin;
    }
}
