package com.example.userlocation.service;

import com.example.userlocation.entity.UserLocation;
import com.example.userlocation.repository.UserLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLocationService {
    @Autowired
    private UserLocationRepository userLocationRepository;

    public List<UserLocation> getNearestUsers(int n) {
        List<UserLocation> allUsers = userLocationRepository.findByExcludedFalse();

        // Print some debugging information
        System.out.println("All Users:");
        allUsers.forEach(user -> System.out.println(user.getId() + ": " + user.getLatitude() + ", " + user.getLongitude()));

        // Sort the users by distance from (0,0) using the Haversine formula
        Collections.sort(allUsers, (user1, user2) -> {
            double distance1 = haversine(0, 0, user1.getLatitude(), user1.getLongitude());
            double distance2 = haversine(0, 0, user2.getLatitude(), user2.getLongitude());
            return Double.compare(distance1, distance2);
        });

        // Print some debugging information
        System.out.println("Sorted Users:");
        allUsers.forEach(user -> System.out.println(user.getId() + ": " + user.getLatitude() + ", " + user.getLongitude()));

        // Return the first N users
        List<UserLocation> nearestUsers = allUsers.stream()
                .limit(n)
                .collect(Collectors.toList());

        // Print some debugging information
        System.out.println("Nearest Users:");
        nearestUsers.forEach(user -> System.out.println(user.getId() + ": " + user.getLatitude() + ", " + user.getLongitude()));

        return nearestUsers;
    }

    // Haversine formula to calculate distance between two points on Earth
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the Earth in kilometers
        final double R = 6371.0;

        // Convert latitude and longitude from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
}



