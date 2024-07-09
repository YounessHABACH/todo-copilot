package sfeir.ynshb;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sfeir.ynshb.dto.TodoDto;
import sfeir.ynshb.model.TodoStatusEnum;
import sfeir.ynshb.service.TodoService;

import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class TodoE2EApplication implements CommandLineRunner {

	private final TodoService todoService;

	public static void main(String[] args) {
		SpringApplication.run(TodoE2EApplication.class, args);
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
						.status(TodoStatusEnum.CREATED)
						.build())
				.forEach(todoService::add);
	}

}
