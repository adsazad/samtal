#!bin/bash

# Create keystore
echo "Refreshing './keys/local.keystore'"
openssl pkcs12 -export \
	 -in /etc/letsencrypt/live/local/cert.pem \
	 -inkey /etc/letsencrypt/live/local/privkey.pem \
	 -out /tmp/mydomain.be.p12 \
	 -name mydomain.be \
	 -CAfile /etc/letsencrypt/live/local/fullchain.pem \
	 -caname "Let's Encrypt Authority X3" \
	 -password pass:changeit
keytool -importkeystore \
	-deststorepass changeit \
	-destkeypass changeit \
	-deststoretype pkcs12 \
	-srckeystore /tmp/local.p12 \
	-srcstoretype PKCS12 \
	-srcstorepass changeit \
	-destkeystore /tmp/local.keystore \
	-alias mydomain.be
# Move certificates to other servers
echo "Copy './keys/local.keystore' to cluster servers"
cp /tmp/mydomain.be.keystore /home/admin_jworks/ssl/mydomain.be.keystore
scp  /tmp/mydomain.be.keystore cc-backend-node-02:/home/admin_jworks/ssl/mydomain.be.keystore
scp  /tmp/mydomain.be.keystore cc-frontend-node-01:/home/admin_jworks/ssl/mydomain.be.keystore

# Create truststore
echo "Refreshing './keys/localt.keystore'"
rm theirdomain.be.keystore
openssl s_client -connect theirdomain.be:443 -showcerts </dev/null 2>/dev/null|openssl x509 -outform DER >theirdomain.der
openssl x509 -inform der -in theirdomain.der -out theirdomain.pem
keytool -import \
	-alias theirdomain \
	-keystore theirdomain.be.keystore \
	-file ./theirdomain.pem \
	-storepass theirdomain \
	-noprompt
echo "Copy './keys/localt.keystore' to cluster servers"
cp theirdomain.be.keystore /home/admin_jworks/ssl/
sudo scp ssl/theirdomain.be.keystore cc-backend-node-02:/home/admin_jworks/ssl/
sudo scp ssl/theirdomain.be.keystore cc-frontend-node-01:/home/admin_jworks/ssl/
