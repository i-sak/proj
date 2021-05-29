package com.project.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.domain.SampleDTO;
import com.project.domain.SampleDTOList;
import com.project.domain.TodoDTO;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
		log.info("initBinder call");
	}
	*/
	
	@RequestMapping("")
	public void basic() {
		log.info("basic...");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name : " + name);
		log.info("age : " + age);
		return "ex02";
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids : " + ids);
		return "ex02List";
	}
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " + list);
		return "ex02Bean";
	}
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo: " + todo );
		return "ex03";
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page : " + page);
		
		return "/sample/ex04";
	}
	
	@GetMapping("/ex04r")
	public String ex04_1(RedirectAttributes rttr) {
		rttr.addAttribute("name", "BBB");
		rttr.addAttribute("age", "22");
		rttr.addAttribute("page", 2);
		return "redirect:/sample/ex04";
	}
	
	@GetMapping("/ex05")
	public void ex05() {
		log.info("ex05...");
	}
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("ex06...");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		return dto;
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("ex07...");
		String msg = "{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK); 
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload...");
	}
	
	@PostMapping("/exUploadPost")
	public String exUploadPost(MultipartFile[] files) { // ArrayList<MultipartFile>
		String uploadFolder = "C:\\ftmp\\storage";
		
		for(MultipartFile file : files) {
			log.info("---------------------");
			log.info("name : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
			
			File saveFile = new File(uploadFolder, file.getOriginalFilename());
			
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				log.info(e.getMessage());
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}

		return "forward:/sample/exUpload"; // "redirect:/sample/exUpload";
		
		
		/* 이것도 가능한지 점검해봐야 할 듯
		files.forEach(file -> {
			log.info("---------------------");
			log.info("name : " + file.getOriginalFilename());
			log.info("size : " + file.getSize());
			
			File saveFile = new File("C:\\ftmp", file.getName());
			
			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException e) {
				log.info(e.getMessage());
			} catch (IOException e) {
				log.info(e.getMessage());
			}
			log.debug("------------------------------------");
		});
		*/
	}
	
	
	
}
