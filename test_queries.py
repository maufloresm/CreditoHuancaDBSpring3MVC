with open("src/baseDeDatos/UsuariosJDBCTemplate.java") as f:
    code = f.read()
    print("Has SQL_INSERT_PAGO:", "SQL_INSERT_PAGO" in code)
    print("Has SQL_SELECT_PAGOS:", "SQL_SELECT_PAGOS" in code)
