package com.example.project.photoz;
// ...existing code...


import com.example.project.photoz.model.Photo;
import com.example.project.photoz.repository.Photozrepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;






@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PhotozIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Photozrepository photozrepository;

	@BeforeEach
	void setup() {
		photozrepository.deleteAll();
	}

	@Test
	void testUploadPhoto() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"data", "test.jpg", "image/jpeg", "dummydata".getBytes()
		);
		mockMvc.perform(multipart("/photoz").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.filename").value("test.jpg"));
	}

	@Test
	void testGetAllPhotos() throws Exception {
		Photo photo = new Photo();
		photo.setId(UUID.randomUUID().toString());
		photo.setFilename("sample.jpg");
		photo.setContentType("image/jpeg");
		photo.setData("abc".getBytes());
		photozrepository.save(photo);

		mockMvc.perform(get("/photoz"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].filename").value("sample.jpg"));
	}

	@Test
	void testGetPhotoById() throws Exception {
		Photo photo = new Photo();
		photo.setId("123");
		photo.setFilename("findme.jpg");
		photo.setContentType("image/jpeg");
		photo.setData("xyz".getBytes());
		photozrepository.save(photo);

		mockMvc.perform(get("/photoz/123"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.filename").value("findme.jpg"));
	}

	@Test
	void testGetPhotoByIdNotFound() throws Exception {
		mockMvc.perform(get("/photoz/doesnotexist"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeletePhoto() throws Exception {
		Photo photo = new Photo();
		photo.setId("delid");
		photo.setFilename("delete.jpg");
		photo.setContentType("image/jpeg");
		photo.setData("del".getBytes());
		photozrepository.save(photo);

		mockMvc.perform(delete("/photoz/delid"))
				.andExpect(status().isOk());

		assertThat(photozrepository.findById("delid")).isEmpty();
	}

	@Test
	void testDownloadPhoto() throws Exception {
		Photo photo = new Photo();
		photo.setId("downid");
		photo.setFilename("download.jpg");
		photo.setContentType("image/jpeg");
		photo.setData("filedata".getBytes());
		photozrepository.save(photo);

		mockMvc.perform(get("/download/downid"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"download.jpg\""))
				.andExpect(content().contentType("image/jpeg"))
				.andExpect(content().bytes("filedata".getBytes()));
	}

	@Test
	void testDownloadPhotoNotFound() throws Exception {
		mockMvc.perform(get("/download/nosuchid"))
				.andExpect(status().isNotFound());
	}
}