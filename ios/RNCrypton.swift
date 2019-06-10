//
//  RNCrypton.swift
//  RNCrypton
//
//  Created by AmirHossein on 6/9/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import CryptoSwift


extension String {
    
    func fromBase64() -> String? {
        guard let data = Data(base64Encoded: self) else {
            return nil
        }
        
        return String(data: data, encoding: .utf8)
    }
    
    func toBase64() -> String {
        return Data(self.utf8).base64EncodedString()
    }
}


@objc(RNCrypton)
class RNCrypton: NSObject {

//    @objc func Hi(_ callback: RCTResponseSenderBlock,_ hello: String) {
//        callback([NSNull(), hello])
//        // Date is ready to use!
//    }
    func stringToBytes(_ string: String) -> [UInt8]? {
        let length = string.count
        if length & 1 != 0 {
            return nil
        }
        var bytes = [UInt8]()
        bytes.reserveCapacity(length/2)
        var index = string.startIndex
        for _ in 0..<length/2 {
            let nextIndex = string.index(index, offsetBy: 2)
            if let b = UInt8(string[index..<nextIndex], radix: 16) {
                bytes.append(b)
            } else {
                return nil
            }
            index = nextIndex
        }
        return bytes
    }
    
    func generateRandomBytes(count: Int) -> String? {
        
        var keyData = Data(count: count)
        let result = keyData.withUnsafeMutableBytes {
            SecRandomCopyBytes(kSecRandomDefault, count, $0.baseAddress!)
        }
        if result == errSecSuccess {
            return keyData.base64EncodedString()
        } else {
            print("Problem generating random bytes")
            return nil
        }
    }
    
    func bytesConvertToHexstring(byte : [UInt8]) -> String {
        var string = ""
        
        for val in byte {
            //getBytes(&byte, range: NSMakeRange(i, 1))
            string = string + String(format: "%02X", val)
        }
        
        return string
    }
    
    

    @objc func AES_CBC_256_encryption(_ message: String,
                  key: String,
                  iv: String,
                  resolver resolve: @escaping RCTPromiseResolveBlock,
                  rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        do {
            let aes = try AES(key: Array(key.utf8), blockMode: CBC(iv: Array(iv.utf8)) ,padding: .pkcs7  )
            let ciphertext = try aes.encrypt(Array(message.utf8))
            let hexstr = bytesConvertToHexstring(byte: ciphertext)
            resolve(hexstr)
        } catch  {
            resolve("error")
        }
    }
    
    @objc func AES_CBC_256_decryption(_ Cipher: String,
                                      key: String,
                                      iv: String,
                                      resolver resolve: @escaping RCTPromiseResolveBlock,
                                      rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        do {
            let aes = try AES(key: Array(key.utf8), blockMode: CBC(iv: Array(iv.utf8)) ,padding: .pkcs7  )
            let ByteArray = Array<UInt8>(hex: Cipher)
            let decrypted_barray = try aes.decrypt(ByteArray)
            let plain_text = String(bytes: decrypted_barray, encoding: .utf8)
            resolve(plain_text)
        } catch  {
            resolve("error")
        }
    }
    
    @objc func AES_CBC_256_pbkdf2_Encrypt(_ message: String,
                                      password: String,
                                      splitter: String,
                                      resolver resolve: @escaping RCTPromiseResolveBlock,
                                      rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
 
        do {
            let ipassword : [UInt8] = Array(password.utf8)
            let salt : [UInt8] = Array<UInt8>(base64: "9DOnvs5jN/I=")
            let iv   : [UInt8] = Array<UInt8>(base64: "QroCK5GxmMalCRuzTErRVQ==")
            let key = try PKCS5.PBKDF2(
                password: ipassword,
                salt: salt,
                iterations: 1000,
                keyLength: 32,
                variant: .sha1
            ).calculate()
            
            let aes = try AES(key: key, blockMode: CBC(iv: iv), padding: .pkcs5)
            let test = Array("hello".utf8)
            let encrypted = try aes.encrypt(test)
            let final = salt.toBase64()!+splitter+iv.toBase64()!+splitter+encrypted.toBase64()!
            resolve(final)
        } catch  {
            resolve("Error: \(error)")
        }
    }
    
    
    @objc func AES_CBC_256_pbkdf2_Decrypt(_ ciphertext: String,
                                          password: String,
                                          splitter: String,
                                          resolver resolve: @escaping RCTPromiseResolveBlock,
                                          rejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        do {
            let fields = ciphertext.components(separatedBy: splitter)
            let salt : [UInt8] = Array<UInt8>(base64: fields[0])
            let iv   : [UInt8] = Array<UInt8>(base64: fields[1])
            let encrypted: [UInt8] = Array<UInt8>(base64: fields[2])
            let ipassword : [UInt8] = Array(password.utf8)
            let key = try PKCS5.PBKDF2(
                password: ipassword,
                salt: salt,
                iterations: 1000,
                keyLength: 32,
                variant: .sha1
                ).calculate()
            let aes = try AES(key: key, blockMode: CBC(iv: iv), padding: .pkcs5)
            let decrypted = try aes.decrypt(encrypted)
            let plain_text = String(bytes: decrypted, encoding: .utf8)
            resolve(plain_text)
        } catch  {
            resolve("Error: \(error)")
        }
    }
    
    
    
    
    
    
    
    @objc
    func constantsToExport() -> [String: Any]! {
        return ["someKey": "someValue"]
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    
    //
  

}



