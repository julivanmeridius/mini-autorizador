CREATE TABLE cartao
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  numero_cartao BIGINT NOT NULL,
  senha INT NOT NULL,
  saldo DECIMAL(19,2) DEFAULT 500.00, 
  
  CONSTRAINT constraint_cartao UNIQUE (numero_cartao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
