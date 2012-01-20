eventCompileStart = { kind ->  
    // Incrementando o número de versao da aplicação 
	BigDecimal versao = metadata.'app.version' as BigDecimal  // Converte de Double para BigDecimal
    if (!versao)  
        versao = 0 
    else 
        versao = versao + 0.01 
    metadata.'app.version' = versao.toString()  
    try {  
        metadata.persist()  
    } 
	catch (Exception ex) {  
        ex.printStackTrace()  
    }  
    println "Nova versao: ${versao}" 
} 
