package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.PaperSpider;
import com.borisov.howtodeservespring.infra.epp.less3spiders.Spider0;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GodModeEnvironmentPostProcessorTest {
    @InjectMocks                               GodModeEPP                     epp;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) ConfigurableEnvironment        env;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) SpringApplication              app;
    @Captor                                    ArgumentCaptor<PropertySource> captor;

    @Test
    void should_add_property_when_less_than_3_spiders_in_classpath() {
        when(app.getMainApplicationClass()).thenAnswer(_ -> Spider0.class);

        //when
        epp.postProcessEnvironment(env, app);


        //then
        verify(env, times(0)).getPropertySources();
    }

    @Test
    void should_add_property_when_more_than_3_spiders_in_classpath() {
        when(app.getMainApplicationClass()).thenAnswer(_ -> PaperSpider.class);

        //when
        epp.postProcessEnvironment(env, app);

        //then
        verify(env.getPropertySources(), times(1))
                .addLast(captor.capture());
        assertThat(captor.getValue().getProperty("god.mode"))
                .as("Must add to last because should be override")
                .isEqualTo(true);
    }
}
