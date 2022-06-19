package com.lowes.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import com.lowes.apigateway.StringConstants;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
	private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// get key from header
		List<String> apiKeyHeader = exchange.getRequest().getHeaders().get("gatewaykey");
		logger.info("api key {} ", apiKeyHeader);
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String routeId = route != null ? route.getId() : null;
		// check the authorization
		if (routeId == null || CollectionUtils.isEmpty(apiKeyHeader) || !isAuthorize(routeId, apiKeyHeader.get(0))) {
			logger.error(StringConstants.UNAUTHORIZED);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, StringConstants.UNAUTHORIZED);
		}
		return chain.filter(exchange);

	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private boolean isAuthorize(String routeId, String apiKey) {
		List<String> routeIds = Arrays.asList(StringConstants.PRODUCT_SERVICE_KEY, StringConstants.ORDER_SERVICE_KEY);
		return routeIds.contains(routeId) && apiKey.equalsIgnoreCase(StringConstants.API_KEY);
	}
}
