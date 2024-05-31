package com.example.bimapplication.bim.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BIMEntityRequest {
    private String id;
    private String name;
    private String role;
    private String data;
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

