package com.services.weather.controllers;


import com.services.weather.config.YAMLConfig;
import com.services.weather.models.WeatherReport;
import com.services.weather.repo.WeatherReportRepo;
import com.services.weather.scheduling.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller
public class WeatherReportController {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private WeatherReportRepo weatherReportRepo;

    @Autowired
    private YAMLConfig yamlConfig;


    @GetMapping("/")
    public String index(Model model)
    {
        Iterable<WeatherReport> weatherReports = weatherReportRepo.findAll();
        model.addAttribute("weatherReports", weatherReports);
        return "index";
    }

    @GetMapping("/add")
    public String reportAdd(Model model)
    {
        return "report-add";
    }

    @PostMapping("/add")
    public String reportLocalityAdd(@RequestParam String name, @RequestParam double latitude, @RequestParam double longtitude, Model model)
    {
        WeatherReport weatherReport = new WeatherReport(yamlConfig.getApiKey(), name, latitude, longtitude);

        weatherReportRepo.save(weatherReport);
        return "redirect:/";
    }

    @GetMapping("/report/{id}")
    public String details(@PathVariable(value = "id") long id, Model model)
    {
        if(!weatherReportRepo.existsById(id))
        {
            return "redirect:/";
        }
        Optional<WeatherReport> weatherReport = weatherReportRepo.findById(id);

        model.addAttribute("weatherReport",weatherReport.orElse(new WeatherReport()));
        return "report-details";
    }

    @GetMapping("/report/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model)
    {
        if(!weatherReportRepo.existsById(id))
        {
            return "redirect:/";
        }
        Optional<WeatherReport> weatherReport = weatherReportRepo.findById(id);

        model.addAttribute("weatherReport",weatherReport.orElse(new WeatherReport()));;
        return "report-edit";
    }


    @PostMapping("/report/{id}/edit")
    public String reportPostUpdate(@PathVariable(value = "id") long id,@RequestParam String name, @RequestParam double latitude, @RequestParam double longtitude, Model model)
    {
        if(!weatherReportRepo.existsById(id))
        {
            return "redirect:/";
        }
        WeatherReport weatherReport = weatherReportRepo.findById(id).orElseThrow();
        weatherReport.setName(name);
        weatherReport.setLatitude(latitude);
        weatherReport.setLongtitude(longtitude);
        weatherReport.setDataViaApi(yamlConfig.getApiKey());
        weatherReportRepo.save(weatherReport);
        return "redirect:/";
    }

    @PostMapping("/report/{id}/remove")
    public String reportPostDelete(@PathVariable(value = "id") long id, Model model)
    {
        if(!weatherReportRepo.existsById(id))
        {
            return "redirect:/";
        }
        WeatherReport weatherReport = weatherReportRepo.findById(id).orElseThrow();
        weatherReportRepo.delete(weatherReport);
        return "redirect:/";
    }

    @Scheduled(fixedDelayString = "${my.property.fixed.delay.seconds}000")
    public String updateAllReports()
    {
        Iterable<WeatherReport> weatherReports = weatherReportRepo.findAll();
        for(WeatherReport weatherReport:weatherReports)
        {
            weatherReport.setDataViaApi(yamlConfig.getApiKey());
            weatherReportRepo.save(weatherReport);
        }
        log.info("All data updated");
        return null;
    }


}
