package runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TestRunner implements CommandLineRunner {

    @Override
    public void run (String... args) throws Exception {
        WebClient client = WebClient.create("http://localhost:8000");
       
       
        /*Flux<Producto> flux=client
        .get()
        .uri("/productos")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Producto.class);
        flux.subscribe(p->System.out.println(p));*/
       /*
       
        client
        .post()
        .uri("/alta") // no accept porque no vamos a recibir nada
        .body(Mono.just(new Producto(200,"prueba","categoriatest",5.0,20)), Producto.class)//tipo que es producto.class
        .retrieve()//nos deuelve un Mono Void
        .bodyToMono(Void.class)
        .doOnTerminate(()->System.out.print("Se ha dado de alta el producto, o no"))
        .block();*/

        /*Mono<Producto> monoFind=client
        .get()
        .uri("/producto?cod=102")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Producto.class);

        monoFind.subscribe(p->System.out.println(p));
        monoFind.switchIfEmpty(Mono.just(new Producto()).map (p->
        {
            System.out.println("No se ha encontrado producto");
            return p;
        }
        )).block();*/



        client
        .delete()
        .uri("/eliminar?cod=130")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(h->h.is4xxClientError(), t->{ // en caso saliera http 40x
            System.out.println("No se encontro el producto");
            return Mono.empty();
        })
        .bodyToMono(Producto.class)
        .subscribe(p->System.out.println(p));








    }
    
}
