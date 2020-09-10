
import { NativeModules } from 'react-native';
var RNCrypton = NativeModules['RNCrypton'];
import errors from './errors'

const warner = (message) => {
    console.warn(message)
}

const string_validator = (value) => {
    return value && typeof value == 'string'? value.trim().length > 0 : false
}

const number_validator = (value) => {
    return value && typeof value == 'number'? value > 0 : false
}

export const randomByte =  (length ) => new Promise((resolve , reject)=> {
     length? typeof length == 'number'? length > 0?
            resolve(RNCrypton.grb_b64(length))
            :reject(errors.main+errors.random_byte+'length must be greater than 0')
            :reject(errors.main+errors.random_byte+'lenght must be number')
            :reject(errors.main+errors.random_byte+"lenght is not provided!\nlenght must be a number and greater than 0")
})

export const Pbkdf2 = {
    encrypt: (text ,password) => new Promise((resolve , reject)=> {
        if(string_validator(text) && string_validator(password)){
            resolve(RNCrypton.AES_CBC_256_pbkdf2_Encrypt(text ,password))
        }else{
            reject(errors.main+errors.pbk_encrypt+'\ntext and password must be string and not empty')
        }
    })
    ,

    decrypt: (encrypted_text , password) => new Promise((resolve , reject)=> {
        if(string_validator(encrypted_text) && string_validator(password)){
            resolve(RNCrypton.AES_CBC_256_pbkdf2_Decrypt(encrypted_text ,password))
        }else{
            reject(errors.main+errors.pbk_decrypt+'\nencrypted_text and password must be string and not empty')
        }
    })
    //not tested yet
    // encryptWithCustomKey: (text ,password ,key_lenght, key_iteration) => new Promise((resolve , reject)=> {
    //     if( string_validator(text) && string_validator(password) && 
    //         number_validator(key_iteration) && number_validator(key_lenght) ){
    //             resolve(RNCrypton.AES_CBC_256_pbkdf2_Encrypt(text ,password ,key_lenght , key_iteration))
    //     }else{
    //         reject(errors.main+errors.pbk_encryptWc+'\ntext and password must be string and not empty\nkey_lenght and key_iteration must be number and not 0')
    //     }
    // })
}
//AES_CBC_256
const AES_CBC_256 = {
    Encrypt:function(Plain_text, Key, iv){ return RNCrypton.AES_CBC_256_encryption(Plain_text ,Key ,iv)},
    Decrypt:function(Encrypted_text ,key ,iv){ return RNCrypton.AES_CBC_256_decryption(Encrypted_text ,key , iv)}
}


//AES_256_pbkdf2
export const AES_256_pbkdf2 ={
    Decrypt:function(Encrypted_text, Password, splitter){ return RNCrypton.AES_CBC_256_pbkdf2_Decrypt(Encrypted_text, Password, splitter) },
    Encrypt:function(Plain_text, Password, splitter){return RNCrypton.AES_CBC_256_pbkdf2_Encrypt(Plain_text, Password, splitter)}
}

export const Hash = (te , ty) => new Promise(resolve => {
    if(ty !== "MD5" && ty !== "SHA-1" && ty !== "SHA-224" && ty !== "SHA-256" && ty !== "SHA-384" && ty !== "SHA-512"){
        resolve('type not suported')
        warner(errors.hash_type)
    }else{
        resolve(RNCrypton.getHash(String(te) , ty))
    }
})

const check_config_base = (config) => {
    console.log('in checking base config')
    if(!config || typeof(config) !== 'object'){ warner(errors.configs) ;return false }
    else if(!config.mode){ warner(errors.config_mode) ; return false }
    else if(config.mode !== 'CBC/NoPadding' && config.mode !== 'CBC/PKCS5Padding' && config.mode !== 'ECB/NoPadding' && config.mode !== 'ECB/PKCS5Padding' ){warner(errors.config_mode); return false}
    else if(!config.text){ warner(errors.config_text); return false }
    else if(config.text == '' || typeof(config.text) !== 'string' ){ warner(errors.config_text) ; return false }
    else if(!config.action){ warner(errors.config_action); return false }
    else if(config.action !== 'encrypt' && config.action !== 'decrypt'){ warner(errors.config_action_empty); return false }
    else{ console.log('base checking successful');return true }
}

const CBC_pbkdf2_config_check = (config) => {

}

const CBC_noPadding_config_check = (config) => {
    console.log('cbc no ppadding config check' , config , config.key == '' ,config.iv == '' ,typeof(config.key) , typeof(config.iv) )
    if(!config.key || !config.iv){ warner(errors.config_KIV); return false }
    else if(config.key == '' || config.iv == '' || typeof(config.key) !== 'string' || typeof(config.iv) !== 'string'){ warner(errors.config_KIV_incorrect); return false }else{ console.log('cbc no padding config check successful');return true }
}

const CBC_PKCS5Padding_config_check = (config) => {
    if(!config.password || !config.iv || !config.salt ){ warner(errors.pbk_req); return false }
    else if(
        config.password == ''                   ||
        config.iv       == ''                   ||
        config.salt     == ''                   ||
        typeof(config.password) !== 'string'    ||
        typeof(config.iv)       !== 'string'    ||
        typeof(config.salt)     !== 'string'    ){ warner(errors.pbk_req_incorrect) ;return false  }
}

export const getKey = () => new Promise( resolve => {

})


export const AES = (config) => new Promise( resolve => {
    if(check_config_base(config)){
        if(config.mode == 'CBC/NoPadding'){
            console.log('entered config to aes:' ,  config)
            CBC_noPadding_config_check(config)? config.action == 'encrypt'? 
            resolve(RNCrypton.AES_CBC_256_encryption(config.text , config.key , config.iv))
            :resolve(RNCrypton.AES_CBC_256_decryption(config.text , config.key , config.iv ))
            :resolve('ops!')
        }else{
            CBC_PKCS5Padding_config_check(config)? config.action == 'encrypt'?
            console.log('pbk encripting'):
            console.log('pbk decrypting'):
            console.log('pbk ........')
        }
    }else{
        resolve('')
    }
    
    
})
    

//=============== HASHING ================
//const sha2  = function(string){ return RNCrypton.getHash(string, "SHA-2")   }
//const sha3  = function(string){ return RNCrypton.getHash(string, "SHA-3")   }
