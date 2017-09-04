package com.zelering.AdReader.lib.model;

import android.os.Bundle;

import com.zelering.AdReader.lib.R;

import java.io.Serializable;

/**
 * Created by lcom64 on 21/7/17.
 */

public class Config implements Serializable {

    String toolbarColor = "#ffffff";
    String toolbarTextColor = "#da1c28";
    String BackgroundColor = "#da1c28";
    String texColor = "#ffffff";
    String TitleColor = "#ffffff";
    String HeadingColor = "#ffffff";
    String ButtonColor = "#A11019";
    int applicationLogo = R.drawable.logoapp;
    int scanImgTitle = R.string.point_camera;
    int homeTitle = R.string.camera_screen_msg;
    int heading = R.string.camera_screen_msg_detail_title;
    int discription = R.string.camera_screen_msg_detail;
    int btnStart =R.string.btn_start;
    String backArrowColor ="#da1c28";

    public Config() {
    }

    public static Builder Builder() {
        return new Builder();
    }


    public static class Builder {

        Config config = new Config();

        public Builder setToolbarColor(String toolbarColor) {
            config.toolbarColor = toolbarColor;
            return Builder.this;
        }

        public Builder setToolbarTextColor(String toolbarTextColor) {
            config.toolbarTextColor = toolbarTextColor;
            return Builder.this;

        }

        public Builder setBackgroundColor(String backgroundColor) {
            config.BackgroundColor = backgroundColor;
            return Builder.this;

        }

        public Builder setTexColor(String texColor) {
            config.texColor = texColor;
            return Builder.this;

        }

        public Builder setTitleColor(String titleColor) {
            config.TitleColor = titleColor;
            return Builder.this;

        }
        public Builder setButtonColor(String ButtonColor) {
            config.ButtonColor = ButtonColor;
            return Builder.this;

        }

        public Builder setHeadingColor(String headingColor) {
            config.HeadingColor = headingColor;
            return Builder.this;

        }

        public Builder setHomeTitle(int homeTitle) {
            config.homeTitle = homeTitle;
            return Builder.this;
        }

        public Builder setScanImgTitle(int scanImgTitle) {
            config.scanImgTitle = scanImgTitle;
            return Builder.this;
        }

        public Builder setHeading(int heading) {
            config.heading = heading;
            return Builder.this;
        }

        public Builder setDiscription(int discription) {
            config.discription = discription;
            return Builder.this;
        }


        public Builder setBtnStart(int btnStart) {
            config.btnStart = btnStart;
            return Builder.this;
        }


        public Builder setAplicationLogo(int applicationLogo) {
            config.applicationLogo = applicationLogo;
            return Builder.this;
        }
        public Builder setBackArrowColor(String backArrowColor) {
            config.backArrowColor = backArrowColor;
            return Builder.this;

        }




        public Config build() {
            return this.config;
        }
    }

    public String getToolbarColor() {
        return toolbarColor;
    }

    public String getToolbarTextColor() {
        return toolbarTextColor;
    }

    public String getBackgroundColor() {
        return BackgroundColor;
    }

    public String getTexColor() {
        return texColor;
    }

    public String getTitleColor() {
        return TitleColor;
    }

    public String getHeadingColor() {
        return HeadingColor;
    }
    public String getButtonColorColor() {
        return ButtonColor;
    }

    public String getBackArrowColor(){
        return backArrowColor;
    }

    public int getApplicationLogo(){
        return applicationLogo;
    }

    public int getScanImgTitle(){
        return scanImgTitle;
    }

    public int getHomeTitle(){
        return homeTitle;
    }

    public int getHeading(){
        return heading;
    }

    public int getDiscription(){
        return discription;
    }

    public int getBtnStart(){
        return btnStart;
    }
}
