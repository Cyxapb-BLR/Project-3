CREATE TABLE Sensor
(
    id   int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE Measurement
(
    id                    int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value                 double precision CHECK ( Measurement.value <= 100.0 and Measurement.value >= -100) NOT NULL,
    raining               boolean                                                                             NOT NULL,
    sensor                varchar(30) references Sensor (name),
    measurement_date_time timestamp                                                                           NOT NULL
);