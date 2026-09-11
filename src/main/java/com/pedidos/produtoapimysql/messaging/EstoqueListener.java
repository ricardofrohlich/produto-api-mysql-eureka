package com.pedidos.produtoapimysql.messaging;

import com.pedidos.produtoapimysql.service.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstoqueListener {
    private final ProdutoService produtoService;
    private final RabbitTemplate rabbitTemplate;

    public EstoqueListener(ProdutoService produtoService, RabbitTemplate rabbitTemplate) {
        this.produtoService = produtoService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_BAIXAR_ESTOQUE)
    public void receber(BaixarEstoqueCommand comando) {

        System.out.println("Mensagem recebida pelo produto-api!");
        System.out.println("Produto: " + comando.getProdutoId());
        System.out.println("Quantidade: " + comando.getQuantidade());

        try {

            // Chama o serviço que já existe
            // para realizar a baixa do estoque.
            produtoService.baixarEstoque(
                    comando.getProdutoId(),
                    comando.getQuantidade()
            );

            // Se chegou aqui, a baixa foi realizada.
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_CONFIRMADO,
                    new ResultadoEstoque(
                            comando.getPedidoId(),
                            comando.getItemId(),
                            comando.getProdutoId(),
                            true,
                            null
                    )
            );

        } catch (IllegalArgumentException e) {

            // Se não foi possível baixar o estoque,
            // enviamos uma resposta de recusado.
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_RECUSADO,
                    new ResultadoEstoque(
                            comando.getPedidoId(),
                            comando.getItemId(),
                            comando.getProdutoId(),
                            false,
                            e.getMessage()
                    )
            );
        }
    }
}
