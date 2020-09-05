package pe.edu.vallegrande.congnitiveface.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pe.edu.vallegrande.congnitiveface.config.FaceEnv;
import pe.edu.vallegrande.congnitiveface.model.FaceModel;

@Controller
public class FaceViewController {

	@Autowired
	private FaceEnv faceEnv;

	@GetMapping("/")
	public String init(Model model) {
		FaceModel faceApiModel = new FaceModel();
		faceApiModel.setKey(faceEnv.getKey());
		model.addAttribute("model", faceApiModel);
		return "detect";
	}

}
