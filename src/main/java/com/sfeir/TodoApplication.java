package com.sfeir;

import com.sfeir.dto.TodoDto;
import com.sfeir.mapper.TodoMapper;
import com.sfeir.model.Todo;
import com.sfeir.model.TodoEnum;
import com.sfeir.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodoApplication implements CommandLineRunner {

	private final TodoService todoService;
	private final TodoMapper todoMapper;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Stream.of(
				new Pair<>("Java", "* this Todo for java to learn Spring Boot"),
				new Pair<>("Angular", "* Angular and Typescript"),
				new Pair<>("Docker", "* Docker 101"),
				new Pair<>("KB8", "* extension of Docker"),
				new Pair<>("Test", "* Test Todo")
		)
				.map(pair -> TodoDto.builder()
						.title(pair.a)
						.description(pair.b)
						.status(TodoEnum.CREATED)
						.build())
				.forEach(todoService::add);
	}
}
