.PHONY: run

run:
	@echo "Running... ./gradlew docker"
	@./gradlew docker
	@echo "Running... docker-compose up"
	@docker-compose up -d