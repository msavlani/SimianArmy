package com.netflix.simianarmy.client.vsphere;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Helper to schedule a maintainance downtime for a host in icinga.
 * @author ingmar.krusch@immobilienscout24.de
 */
public abstract class MonitoringDisable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringDisable.class);

    private static final long MINUTES_IN_SECONDS = 60;

    //    private MonitoringDisable() {
    //    }
    //    /**
    //     * lalala.
    //     * @param args
    //     * @throws Exception
    //     */
    //    public static void main(String[] args) throws Exception {
    //        pauseIcingaAlarming("tuvmnk01", 60);
    //    }
    //

    /**
     * Schedule a maintainance downtime for the given host in icinga.
     *
     * @param vmShortName e.g. tuvweb01
     * @param durationInMinutes of the scheduled downtime
     * @throws IOException if the icinga call fails
     */
    public static void pauseIcingaAlarming(String vmShortName, int durationInMinutes) throws IOException {
        BufferedReader in = null;
        try {
            StringBuilder url = new StringBuilder();
            long startTimestamp = new Date().getTime() / 1000; // in seconds
            long endTimestamp = startTimestamp + (durationInMinutes * MINUTES_IN_SECONDS);
            String downtimeType = "1";
            String triggerID = "0";
            String duration = "0";
            // Example URL we need to create (/wo the line break of course):
            // http://tuvica01.rz.is:8080/cmd?q=SCHEDULE_HOST_SVC_DOWNTIME;tuvmnk01
            //        ;1379061100;1379061400;1;0;0;ralph;chaos_monkey
            url
            .append("http://tuvica01.rz.is:8080/cmd?q=SCHEDULE_HOST_SVC_DOWNTIME;")
            .append(vmShortName)
            .append(";").append(startTimestamp)
            .append(";").append(endTimestamp)
            .append(";").append(downtimeType)
            .append(";").append(triggerID)
            .append(";").append(duration)
            .append(";ChaosMonkey;terminating instance for resilience reasons");
            URL diableURL = new URL(url.toString());
            in = new BufferedReader(new InputStreamReader(diableURL.openStream()));
            String inputLine;
            StringBuilder result = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            LOGGER.info("scheduling a icinga downtime for host " + vmShortName + " : " + result);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}