package app;

import static spark.Spark.*;

import service.Principal;

public class Aplicacao {
	
	private static Principal principal = new Principal();
	
    public static void main(String[] args) {
        port(6789);

        post("/produto", (request, response) -> principal.add(request, response));

        get("/produto/:id", (request, response) -> principal.get(request, response));

        get("/produto/update/:id", (request, response) -> principal.update(request, response));

        get("/produto/delete/:id", (request, response) -> principal.remove(request, response));

        get("/produto", (request, response) -> principal.getAll(request, response));
               
    }
}
