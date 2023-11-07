package com.oopmp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Controller{
    @FXML
    Label feelsLike;
    @FXML
    Label tempField;
    @FXML
    Label humField;
    @FXML
    Label precField;
    @FXML
    Label dateLabel;

    private Image state = new Image(getClass().getResource("/com/oopmp/assets/sunny-cloudy.png").toExternalForm());


    @FXML
    ImageView stateView = new ImageView(state);
    Image hum = new Image(getClass().getResource("/com/oopmp/assets/humidity.png").toExternalForm());
    @FXML
    ImageView humView = new ImageView(hum);
    Image prec = new Image(getClass().getResource("/com/oopmp/assets/precipitation.png").toExternalForm());
    @FXML
    ImageView precView = new ImageView(prec);


    Image search = new Image(getClass().getResource("/com/oopmp/assets/search.png").toExternalForm());
    @FXML
    ImageView searchView = new ImageView(search);

    @FXML
    private Label timeLabel;

    LocalDate date = LocalDate.now();
    DateTimeFormatter customDateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
    String fdate = date.format(customDateFormat);
    String temp = "28°C";
    String humText = "Humidity: 0.2";
    String precText = "Precipitation: 70%";
    String feelsLikeText = "Feels like 30°C";
    String json;

    @FXML
    TextField cityQuery;
    public Image changeState(String s){
        switch(s){
            case "Sunny":
                state = new Image(getClass().getResource("/com/oopmp/assets/sunny.png").toExternalForm());
                break;
            case "Overcast":
            case "Cloudy":
            case "Smoky":
                state = new Image(getClass().getResource("/com/oopmp/assets/cloudy.png").toExternalForm());
                break;
            case "Partly Cloudy":
            case "Mist":
            case "Fog":
                state = new Image(getClass().getResource("/com/oopmp/assets/sunny-cloudy.png").toExternalForm());
                break;
            case "Patchy rain possible":
            case "Patchy light drizzle":
            case "Light drizzle":
            case "Freezing drizzle":
            case "Patchy light rain":
            case "Light rain":
            case"Moderate rain at times":
            case"Moderate rain":
                state = new Image(getClass().getResource("/com/oopmp/assets/rain.png").toExternalForm());
                break;
            case "Thundery outbreaks possible":
            case "Blizzard":
            case "Heavy freezing drizzle":
            case"Heavy rain at times":
            case "Heavy rain":
                state = new Image(getClass().getResource("/com/oopmp/assets/storm.png").toExternalForm());
                break;
            default:
                state = new Image(getClass().getResource("/com/oopmp/assets/sunny-cloudy.png").toExternalForm());
        }
        return state;
    }

    public void getData(ActionEvent e) throws Exception{
        String query = cityQuery.getText();
        json =ApiHandler.getJSON(query);

        WeatherDataParser jsonExtract = new WeatherDataParser(json);

        feelsLikeText="Feels like "+ jsonExtract.getFeelsLike()+"°C";
        humText="Humidity: " +jsonExtract.getHumidity()+"%";
        precText= "Precipitation: "+ jsonExtract.getPrecipitation();
        String stateString = jsonExtract.getWeatherDescriptions();
        System.out.println(stateString);
        temp= jsonExtract.getTemperature()+"°C";
        Image stateReturn = changeState(stateString);

        stateView.setImage(stateReturn);
        feelsLike.setText(feelsLikeText);
        humField.setText(humText);
        precField.setText(precText);
        tempField.setText(temp);
    }

    public int CtoF(int x){
        return (x*9/5)+32;
    }

    private boolean celsius = true;
    public void convertUnit(ActionEvent e) throws Exception {
        WeatherDataParser jsonExtract = new WeatherDataParser(json);
        if(json != ""){
        if(celsius) {
            feelsLikeText = "Feels like " + CtoF(Integer.parseInt(jsonExtract.getFeelsLike())) + "°F";
            temp = CtoF(Integer.parseInt(jsonExtract.getTemperature())) + "°F";
        }

        else {
            feelsLikeText="Feels like "+ jsonExtract.getFeelsLike()+"°C";
            temp= jsonExtract.getTemperature()+"°C";
        }
        celsius = !celsius;
        feelsLike.setText(feelsLikeText);
        tempField.setText(temp);}
        else {return; }
    }



    public void initialize() {
        feelsLike.setText(feelsLikeText);
        tempField.setText(temp);
        precField.setText(precText);
        humField.setText(humText);
        dateLabel.setText(""+fdate);

        cityQuery.setOnMouseClicked(e -> cityQuery.clear());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimeLabel(formatter)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void updateTimeLabel(DateTimeFormatter formatter) {
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(formatter);
        timeLabel.setText(formattedTime);
    }


}