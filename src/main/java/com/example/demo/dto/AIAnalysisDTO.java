package com.example.demo.dto;

import java.util.List;

public class AIAnalysisDTO {
    private String riskLevel; // e.g., "HIGH", "LOW"
    private String behaviorPattern; // e.g., "IMPULSIVE SPENDER"
    private List<String> flaggedTransactions; // IDs of unusual spending
    private String humanAdvice;

    // 🔥 1. The new list to catch the Python data
    private List<ClusterPoint> clusterData;

    // --- Getters and Setters ---
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getBehaviorPattern() { return behaviorPattern; }
    public void setBehaviorPattern(String behaviorPattern) { this.behaviorPattern = behaviorPattern; }

    public List<String> getFlaggedTransactions() { return flaggedTransactions; }
    public void setFlaggedTransactions(List<String> flaggedTransactions) { this.flaggedTransactions = flaggedTransactions; }

    public String getHumanAdvice() { return humanAdvice; }
    public void setHumanAdvice(String humanAdvice) { this.humanAdvice = humanAdvice; }

    // 🔥 2. Getter and Setter for the new list
    public List<ClusterPoint> getClusterData() { return clusterData; }
    public void setClusterData(List<ClusterPoint> clusterData) { this.clusterData = clusterData; }

    // 🔥 3. The Inner Class defining the coordinates
    public static class ClusterPoint {
        private double spend;
        private int frequency;
        private int cluster;

        public double getSpend() { return spend; }
        public void setSpend(double spend) { this.spend = spend; }

        public int getFrequency() { return frequency; }
        public void setFrequency(int frequency) { this.frequency = frequency; }

        public int getCluster() { return cluster; }
        public void setCluster(int cluster) { this.cluster = cluster; }
    }
}