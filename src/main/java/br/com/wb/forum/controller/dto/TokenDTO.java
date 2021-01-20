package br.com.wb.forum.controller.dto;

public class TokenDTO {
	
	public String token;
	public String tipo;

	public TokenDTO(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}
}
