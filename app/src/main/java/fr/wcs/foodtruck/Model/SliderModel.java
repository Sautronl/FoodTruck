package fr.wcs.foodtruck.Model;

/**
 * Created by Poto on 20/05/2018.
 */

public class SliderModel {

    private String slider;

    public SliderModel(){

    }

    public SliderModel(String sliderOne){
        this.slider = sliderOne;
    }


    public String getSliderOne() {
        return slider;
    }

    public void setSliderOne(String sliderOne) {
        this.slider = sliderOne;
    }

}
