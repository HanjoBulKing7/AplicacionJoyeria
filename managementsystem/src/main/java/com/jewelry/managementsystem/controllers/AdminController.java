package com.jewelry.managementsystem.controllers;


import com.jewelry.managementsystem.payload.DashboardDTO;
import com.jewelry.managementsystem.services.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("/admin/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard() {

        DashboardDTO dashboardResponse = adminService.getDashboardStats();

        return ResponseEntity.ok()
                .body( dashboardResponse );
    }
}
