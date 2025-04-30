package com.tenpo.api.external.service;

import com.tenpo.api.external.exception.ExternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalPercentageService {

	private final RestTemplate restTemplate;

	//	private static final String RANDOM_API_URL = "https://www.randomnumberapi.com/api/v1.0/random?min=1&max=20&count=1";
	private static final String RANDOM_API_URL = "http://localhost:9010/test";

	@Cacheable(value = "percentageCache")
	public BigDecimal getPercentage() {
		return fetchPercentage();
	}

	public BigDecimal fetchPercentage() {
		log.info("Obteniendo porcentaje (consultando servicio externo o usando cache)...");
		try {
			Integer response = restTemplate.getForObject(RANDOM_API_URL, Integer.class);

			if (response == null) {
				log.error("Respuesta vacía del servicio externo");
				throw new ExternalException("Respuesta vacía del servicio externo");
			}

			log.info("Porcentaje recibido del servicio externo: {}", response);
			return BigDecimal.valueOf(response);
		}
		catch (Exception e) {
			log.error("Error al obtener porcentaje de la API externa: {}", e.getMessage());
			throw new ExternalException(e.getMessage());
		}
	}
}