dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}

//    DATASOURCE PARA O MYSQL

// dataSource {
// 	   pooled = true
// 	   driverClassName = "com.mysql.jdbc.Driver"
// 	   username = "seu_usuario"
//	   password = "sua_senha"
//	   properties {
//	       maxActive = 50
//	       maxIdle = 25
//	       minIdle = 5
//	       initialSize = 5
//	       minEvictableIdleTimeMillis = 60000
//	       timeBetweenEvictionRunsMillis = 60000
//	       maxWait = 10000
//	       validationQuery = "/* ping */"
//	   }
// }
// maxIdle: número máximo de conexões em stand by que você quer manter
// initialSize: número inicial de conexões com o banco de dados
// minEvictableIdleTimeMillis: tempo mínimo em milisegundos para que o pool de conexões comece a fechar conexões em idle
// timeBetweenEvictionRunsMillis: o tempo entre as limpezas de conexão em milisegundos
// maxWait: tempo máximo de espera em milisegundos para se obter uma conexão ou de espera de resultado de uma conexão
// validationQuery: qualquer comando a ser enviado para o SGBD apenas para verificar se a conexão está válida ou não.

// Como usar JNDI?

// A melhor opção é, ao invés de incluir suas configurações de acesso ao BD no DataSources.groovy, usar o JNDI. Assim 
// você transfere a responsabilidade pela configuração do pool e outros aspectos do acesso ao banco de dados para o 
// responsável pela manutenção do seu servidor de aplicações. Além disto, também evita de ficar incluindo senhas e 
// nomes de usuários em um arquivo que qualquer um possa ver. Nestes casos, usamos a propriedade jndiName, tal como 
// no exemplo abaixo:
//
//    production {
//           jndiName = "meu_nome_jndi"
//    }

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
			logSql = "true"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}

// Propriedade dbCreate
// ====================

// A propriedade dbCreate é uma das mais úteis e perigosas do Grails. Nós a usamos para hibernate.hbm2ddl.auto, 
// e pode receber os seguintes valores:
//
// create – Cria toda a estrutura de banco de dados pra você no momento em que a aplicação for iniciada, e 
//          também apaga todos os dados contidos nas tabelas caso estas já existam.
// create-drop - Funciona da mesma maneira que create. A diferença é que quando a aplicação é finalizada, 
//               todas as tabelas são destruídas.
// update – Atualiza as tabelas do banco de dados. Caso estas não existam, são criadas. Se novos atributos 
//          forem incluídos em suas classes, estes serão refletidos em suas tabelas também. No entanto, 
//          algumas mudanças não serão efetuadas. Se você por exemplo tiver renomeado campos, por exemplo, 
//          o GORM não saberá como lidar com isto (não ocorrerão erros). É importante notar também que caso 
//          atributos sejam excluídos de suas classes de domínio, isto não implicará na remoção de campos 
//          nas tabelas relacionadas.
// validate – simplesmente verifica se as suas classes de domínio estão de acordo com as tabelas do banco 
//            de dados. Nenhuma alteração é feita no banco de dados.

// Por medida de segurança, no ambiente de produção você deve simplesmente excluir esta propriedade. Assim 
// não correrá o risco de acidentalmente apagar ou modificar uma estrutura de dados que já esteja sendo 
// usada por seus usuários.

