package com.ibm.watson.nlu.nluvisual.controller;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;

import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class AppController {

	NLUService nluService = new NLUService();
	NaturalLanguageUnderstanding service = nluService.getService();

	@PostMapping("/fileUpload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			Model redirectAttributes) throws Exception {

		//Initialize Apache Tika Parser
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		ParseContext parseContext = new ParseContext();

		//Pass file input stream to Tika parser
		parser.parse(file.getInputStream(), handler, metadata, parseContext);

		//Prepare NLU request
		ConceptsOptions conceptsOptions = new ConceptsOptions.Builder().limit(15).build();
		KeywordsOptions keywordsOptions = new KeywordsOptions.Builder().sentiment(false).limit(15).emotion(false).build();
		EntitiesOptions entitiesOptions = new EntitiesOptions.Builder().limit(15).build();
		SentimentOptions sentimentOptions = new SentimentOptions.Builder().build();
		EmotionOptions emotionOptions = new EmotionOptions.Builder().build();

		Features features = new Features.Builder().entities(entitiesOptions).concepts(conceptsOptions).keywords(keywordsOptions).
				emotion(emotionOptions).sentiment(sentimentOptions).build();
		
		//Pass the text extracted by Tika in NLU request
		AnalyzeOptions parameters = new AnalyzeOptions.Builder().text(handler.toString()).features(features).build();
		
		//execute NLU request
		AnalysisResults results = service.analyze(parameters).execute();

		//set NLU response in model
		redirectAttributes.addAttribute("nluJSON",results.toString());
		processSentiments(redirectAttributes, results);

		return "visual";
	}


	private void processSentiments(Model redirectAttributes, AnalysisResults results) {
		redirectAttributes.addAttribute("neutralDocSentiment","");
		redirectAttributes.addAttribute("positiveDocSentiment","");
		redirectAttributes.addAttribute("negativeDocSentiment","");

		if(results.getSentiment().getDocument().getScore()<0) {  //negative sentiment
			Double negSentiment = results.getSentiment().getDocument().getScore()*100;
			redirectAttributes.addAttribute("negativeDocSentiment",Math.abs(negSentiment.intValue()));

		} else if (results.getSentiment().getDocument().getScore()>0) { //positive sentiment
			Double posSentiment = results.getSentiment().getDocument().getScore()*100;
			redirectAttributes.addAttribute("positiveDocSentiment",posSentiment.intValue());
		}

		//emotions
		if(results.getEmotion()!=null) {
			redirectAttributes.addAttribute("joyScore",new Double(results.getEmotion().getDocument().getEmotion().getJoy()*100).intValue());
			redirectAttributes.addAttribute("fearScore",new Double(results.getEmotion().getDocument().getEmotion().getFear()*100).intValue());
			redirectAttributes.addAttribute("disgustScore",new Double(results.getEmotion().getDocument().getEmotion().getDisgust()*100).intValue());
			redirectAttributes.addAttribute("sadScore",new Double(results.getEmotion().getDocument().getEmotion().getSadness()*100).intValue());
			redirectAttributes.addAttribute("angerScore",new Double(results.getEmotion().getDocument().getEmotion().getAnger()*100).intValue());	
		}
	}

}