package fr.wcs.foodtruck.Model;

public class SliderModel {

    private String sliderUrl;

    public SliderModel(){

    }

    public SliderModel(String sliderUrl){
        this.sliderUrl = sliderUrl;
    }

    public String getSliderUrl() {
        return sliderUrl;
    }

    public void setSliderUrl(String sliderUrl) {
        this.sliderUrl = sliderUrl;
    }
}
