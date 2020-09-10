
import { NativeModules } from 'react-native';

const { RNCrypton } = NativeModules;

//AES_CBC_256
const AES_CBC_256 = {
    Encrypt:function(Plain_text, Key, iv){ return RNCrypton.AES_CBC_256_encryption(Plain_text ,Key ,iv)},
    Decrypt:function(Encrypted_text ,key ,iv){ return RNCrypton.AES_CBC_256_decryption(Encrypted_text ,key , iv)}
}


//AES_256_pbkdf2
const AES_256_pbkdf2 ={
    Decrypt:function(Encrypted_text, Password, splitter){ return RNCrypton.AES_CBC_256_pbkdf2_Decrypt(Encrypted_text, Password, splitter) },
    Encrypt:function(Plain_text, Password, splitter){return RNCrypton.AES_CBC_256_pbkdf2_Encrypt(Plain_text, Password, splitter)}
}

//=============== HASHING ================
const md5   = function(string){ return RNCrypton.getHash(string, "MD5")                }
const sha1  = function(string){ return RNCrypton.getHash(string, "SHA-1")   }
const sha224= function(string){ return RNCrypton.getHash(string, "SHA-224") }
const sha256= function(string){ return RNCrypton.getHash(string, "SHA-256") }
const sha384= function(string){ return RNCrypton.getHash(string, "SHA-384") }
const sha512= function(string){ return RNCrypton.getHash(string, "SHA-512") }
//const sha2  = function(string){ return RNCrypton.getHash(string, "SHA-2")   }
//const sha3  = function(string){ return RNCrypton.getHash(string, "SHA-3")   }

export const hash = function(string , methode){ return RNCrypton.getHash(string , methode) }
const mudoles = {
    AES_256_pbkdf2  : AES_256_pbkdf2,
    AES_CBC_256     : AES_CBC_256,
    MD5             : md5,
    SHA_1           : sha1,
    SHA_224         : sha224,
    SHA_256         : sha256,
    SHA_384         : sha384,
    SHA_512         : sha512,
    //SHA_2           : sha2,
    //SHA_3           : sha3
}


export default mudoles;
