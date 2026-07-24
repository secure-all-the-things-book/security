package com.example.security.authorization.requests;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// <.>
@Controller
@ResponseBody
@RequestMapping("/api")
class CmsController {

	record Article(int id) {
	}

	record ArticleFile(int id) {
	}

	// <.>
	@GetMapping("/articles/{articleId}")
	Article article(@PathVariable int articleId) {
		IO.println("looking up article #" + articleId);
		return new Article(articleId);
	}

	// <.>
	@DeleteMapping("/admin/users/{userId}")
	void delete(@PathVariable long userId) {
		IO.println("going to delete " + userId);
	}

	// <.>
	@GetMapping("/files/file.{fileId}")
	ArticleFile file(@PathVariable int fileId) {
		IO.println("looking up file #" + fileId);
		return new ArticleFile(fileId);
	}

}
