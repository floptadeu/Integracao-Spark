package service;

import dao.DAO;
import model.Cars;
import spark.Request;
import spark.Response;


public class Principal {

	private DAO dao = new DAO();

	public Principal(){
		dao.conectar();
	}

	public Object add(Request request, Response response) {
		int code = Integer.parseInt(request.queryParams("code"));
		int year = Integer.parseInt(request.queryParams("year"));
		String name = request.queryParams("name");
		String manufacturer = request.queryParams("manufacturer");

		int id = dao.getMaxId() + 1;

		Cars Cars = new Cars(code, year, name, manufacturer);
		
		dao.conectar();
		dao.includeCar(Cars);
		dao.close();
		
		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		dao.conectar();
		Cars Cars = (Cars) dao.get(id);
		dao.close();
		
		if (Cars != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<Cars>\n" + 
					"\t<id>" + Cars.getCode() + "</id>\n" +
					"\t<name>" + Cars.getName() + "</name>\n" +
					"\t<year>" + Cars.getYear() + "</year>\n" +
					"\t<manufacturer>" + Cars.getManufacturer() + "</M=manufacturer>\n" +
					"</Cars>\n";
		} else {
			response.status(404); // 404 Not found
			return "Carro " + id + " não encontrado.";
		}

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        dao.conectar();
		Cars Cars = (Cars) dao.get(id);
		dao.close();
		
        if (Cars != null) {
        	Cars.setName(request.queryParams("name"));
        	Cars.setManufacturer(request.queryParams("manufacturer"));
        	Cars.setYear(Integer.parseInt(request.queryParams("year")));
        	
        	dao.conectar();
        	dao.updateCar(Cars);
        	dao.close();
            return id;
        } else {
        	
            response.status(404); // 404 Not found
            return "Carro não encontrado.";
        }

	}

	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        dao.conectar();
        Cars Cars = (Cars) dao.get(id);

        if (Cars != null) {

            dao.deleteCar(id);
            dao.close();
            response.status(200); // success
        	return id;
        } else {
        	dao.close();
            response.status(404); // 404 Not found
            return "Carro não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<series type=\"array\">");

		dao.conectar();

		for (Cars Cars : dao.getCar()) {
			returnValue.append("<Cars>\n" + 
				"\t<id>" + Cars.getCode() + "</id>\n" +
				"\t<name>" + Cars.getName() + "</name>\n" +
				"\t<year>" + Cars.getYear() + "</year>\n" +
				"\t<manufacturer>" + Cars.getManufacturer() + "</M=manufacturer>\n" +
            "</Cars>\n");
		}

		dao.close();
		returnValue.append("</Cars>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}