# Criação da VPC
resource "aws_vpc" "internet_access_vpc" {
  cidr_block                = "172.0.0.0/16"
  enable_dns_hostnames      = true

  tags = {
    Name                    = "internet_access_vpc"
  }
}

# Criação da Subnet Pública
resource "aws_subnet" "internet_access_public_subnet" {
  vpc_id                    = aws_vpc.internet_access_vpc.id
  cidr_block                = "172.0.0.0/16"
  map_public_ip_on_launch   = true

  tags = {
    Name                    = "internet_access_public_subnet"
  }
}

# Criação do Internet Gateway
resource "aws_internet_gateway" "internet_access_igw" {
  vpc_id                    = aws_vpc.internet_access_vpc.id

  tags = {
    Name                    = "internet_access_igw"
  }
}

# Criação da Tabela de Roteamento
resource "aws_route_table" "internet_access_rt" {
  vpc_id                    = aws_vpc.internet_access_vpc.id

  route {
    cidr_block              = "0.0.0.0/0"
    gateway_id              = aws_internet_gateway.internet_access_igw.id
  }

  tags = {
    Name                    = "internet_access_rt"
  }
}

# Criação da Rota Default para Acesso à Internet
resource "aws_route" "internet_access_routetointernet" {
  route_table_id            = aws_route_table.internet_access_rt.id
  destination_cidr_block    = "0.0.0.0/0"
  gateway_id                = aws_internet_gateway.internet_access_igw.id
}

# Associação da Subnet Pública com a Tabela de Roteamento
resource "aws_route_table_association" "internet_access_pub_association" {
  subnet_id                 = aws_subnet.internet_access_public_subnet.id
  route_table_id            = aws_route_table.internet_access_rt.id
}

resource "aws_network_interface" "internet_access_network_interface_blocking" {
  subnet_id                 = aws_subnet.internet_access_public_subnet.id
  security_groups           = [aws_security_group.http_access.id]

  attachment {
    instance                = aws_instance.prd-blocking-application.id
    device_index            = 1
  }
  depends_on                = [aws_security_group.http_access]
}

resource "aws_network_interface" "internet_access_network_interface_reactive" {
  subnet_id                 = aws_subnet.internet_access_public_subnet.id
  security_groups           = [aws_security_group.http_access.id]

  attachment {
    instance                = aws_instance.prd-reactive-application.id
    device_index            = 1
  }
  depends_on                = [aws_security_group.http_access]
}
