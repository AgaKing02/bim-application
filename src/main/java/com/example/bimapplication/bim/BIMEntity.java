package com.example.bimapplication.bim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
public class BIMEntity {
    private String id;
    private String name;
    private String role;
    private String data;
    private LocalDateTime timestamp;

    // BIM-specific fields
    private String location;
    private String material;
    private double length;
    private double width;
    private double height;
    private double latitude;
    private double longitude;
    private String projectId;

}

