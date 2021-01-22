package com.example.batchdemo.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.batchdemo.entity.FoodEntity;
import com.example.batchdemo.entity.PromoFoodEntity;
import com.example.batchdemo.repository.FoodRepository;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.CrudRepository;

@Configuration
public class BatchStep1Config {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean("step1ItemReader")
    public RepositoryItemReader<FoodEntity> read(final FoodRepository repository) {

        // Required
        final Map<String, Direction> sorts = new HashMap<String, Direction>();
        sorts.put("id", Direction.ASC);

        return new RepositoryItemReaderBuilder<FoodEntity>().repository(repository).methodName("findByActive").arguments(true).sorts(sorts)
                .saveState(false).build();
    }

    @Bean("step1ItemProcessor")
    public ItemProcessor<FoodEntity, PromoFoodEntity> process() {

        return new ItemProcessor<FoodEntity, PromoFoodEntity>() {

            @Override
            public PromoFoodEntity process(FoodEntity entity) throws Exception {
                final String name = entity.getName();
                
                final PromoFoodEntity promoFoodEntity = new PromoFoodEntity();
                promoFoodEntity.setId(UUID.randomUUID());
                promoFoodEntity.setName(name);

                return promoFoodEntity;
            }
        };
    }

    @Bean("step1ItemWriter")
    public RepositoryItemWriter<PromoFoodEntity> write(final CrudRepository<PromoFoodEntity, UUID> repository) {
        return new RepositoryItemWriterBuilder<PromoFoodEntity>().repository(repository).methodName("save").build();
    }

    @Bean
    public Step step1(RepositoryItemReader<FoodEntity> reader,
                      ItemProcessor<FoodEntity, PromoFoodEntity> processor,
                      RepositoryItemWriter<PromoFoodEntity> writer) {
        return stepBuilderFactory.get("Step - Import Promo Foods Data")
                .<FoodEntity, PromoFoodEntity>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
