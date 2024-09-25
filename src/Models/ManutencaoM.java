package Models;

import java.util.Date;

public class ManutencaoM extends VeiculoM {
    private int idManutencao;
    private Date dataservico;
    private int tempoServico;
    private float custo;
    private boolean status;

    public ManutencaoM(String chassi, String marca, String versao, int ano, String modelo, String cor, float preco,
            Date dataVenda, boolean codicao, int idManutencao, Date dataservico, int tempoServico, float custo,
            boolean status) {
        super(chassi, marca, versao, ano, modelo, cor, preco, dataVenda, codicao, custo, tempoServico);
        this.idManutencao = idManutencao;
        this.dataservico = dataservico;
        this.tempoServico = tempoServico;
        this.custo = custo;
        this.status = status;
    }

    public int getIdManutencao() {
        return idManutencao;
    }

    public void setIdManutencao(int idManutencao) {
        this.idManutencao = idManutencao;
    }

    public Date getDataservico() {
        return dataservico;
    }

    public void setDataservico(Date dataservico) {
        this.dataservico = dataservico;
    }

    public int getTempoServico() {
        return tempoServico;
    }

    public void setTempoServico(int tempoServico) {
        this.tempoServico = tempoServico;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // FUNCAO DE VERIFICAR QUAL MANUTENCAO PRECISA
    public static void verificarManutencao(double kmRodados) {
        int intervalo = 10000;
        int ultimaManutencao = (int) (kmRodados / intervalo) * intervalo;
        int proximaManutencao = ultimaManutencao + intervalo;
        if (kmRodados < ultimaManutencao) {
            System.out.println("O carro deve fazer a manutenção de " + ultimaManutencao + " km.");
        } else if (kmRodados >= ultimaManutencao && kmRodados < proximaManutencao) {
            if (kmRodados - ultimaManutencao < intervalo * 0.1) {
                System.out.println("O carro deve fazer a manutenção de " + ultimaManutencao + " km.");
            } else if (proximaManutencao - kmRodados < intervalo * 0.1) {
                System.out.println("O carro deve se preparar para a manutenção de " + proximaManutencao + " km.");
            } else {
                System.out.println("O carro está dentro da quilometragem de operação normal.");
            }
        } else {
            System.out.println("O carro passou do intervalo de manutenção. Verifique a manutenção de "
                    + proximaManutencao + " km.");
        }
    }

    // FUNCAO DE VERIFICAR QUANTO TEMPO DE SERVIÇO SERA NECESSÁRIO
    public int tempoServico() {
        int horasTrabalho = 8; // 1 dia de trabalho por 10mil km rodado
        float kmAtual = this.getKmAtual();
        int multiplicador = 0;

        // Loop para calcular o multiplicador baseado na quilometragem
        for (int kmLimite = 15000; kmLimite <= 55000; kmLimite += 10000) {
            multiplicador++;
            if (kmAtual < kmLimite) {
                this.tempoServico = horasTrabalho * multiplicador;
                return this.tempoServico;
            }
        }
        this.tempoServico = horasTrabalho * 7; // Mais de uma semana
        return this.tempoServico;
    }

    // FUNCAO PARA CALCULAR O CUSTO POR HORA BASEADA NA KM ATUAL DO VEICULO
    public void tempoCusto(float kmAtual) {
        int tempo = tempoServico();
        int custoPorHora = 10; // Custo por hora
        int multiplicador = 0;

        // Loop para calcular o custo baseado na quilometragem
        for (int kmLimite = 15000; kmLimite <= 55000; kmLimite += 10000) {
            multiplicador++;
            if (kmAtual < kmLimite) {
                this.custo = tempo * custoPorHora * multiplicador;
                break;
            }
        }

        // Verifica quilometragem acima de 55.000 km
        if (kmAtual > 55000 && kmAtual < 100000) {
            multiplicador = 7;
            this.custo = tempo * custoPorHora * multiplicador;
        } else if (kmAtual >= 100000) {
            multiplicador = 14; // Ajustar multiplicador para quilometragens acima de 100k
            this.custo = tempo * custoPorHora * multiplicador;
        }

        System.out.println("Tempo estimado para o serviço: " + tempo + " horas");
        System.out.println("Custo estimado para o serviço: R$ " + custo);
    }

    // FUNCAO PARA IMPRIMIR DETALHES DA MANUTENCAO
    public void imprimeManutencao() {
        System.out.println("\n------------------Detalhes da Manutenção------------------");
        System.out.println("ID da Manutenção: " + idManutencao);
        System.out.println("Data do Serviço: " + dataservico);
        tempoCusto(this.getKmAtual());
        verificarManutencao(this.getKmAtual());
        System.out.println("Status da Manutenção: " + (status ? "Concluída" : "Pendente"));
    }
}