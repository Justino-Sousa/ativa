package br.com.js.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.js.storage.StorageException;
import br.com.js.storage.StorageFileNotFoundException;
import br.com.js.storage.StorageProperties;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;
	StorageProperties properties = new StorageProperties();

	@Autowired
	public FileSystemStorageService() {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public String store(MultipartFile file, String fileName) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}

			try (InputStream inputStream = file.getInputStream()) {
				properties.setFullLocation(destinationFile.toString().replace(file.getOriginalFilename().toString(), "")
						.replace(rootLocation.toString(), "").replace("\\\\", "\\"));
				String newName = destinationFile.toString().replace(file.getOriginalFilename().toString(),
						fileName.replace(properties.getLocation().toString() + "/", ""));
				Path finalName = Path.of(newName);
				Files.copy(inputStream, finalName, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
		return properties.getFullLocation().toString();

	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
