
require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))
version = package['version']

Pod::Spec.new do |s|
  s.name         = "ReactNativeTwitterKit"
  s.version      = version
  s.summary      = "A native TwitterKit react native component."
  s.description  = <<-DESC
                  Twitterkit
                   DESC
  s.homepage     = "https://github.com/netceteragroup/react-native-twitterkit"
  s.license      = "MIT"
  s.author       = "Netcetera"
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/netceteragroup/react-native-twitterkit", :branch => "master" }
  s.source_files  = "ios/*.{h,m}"
  s.public_header_files = "ios/*.h"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "Fabric"
  s.dependency "TwitterKit"
  #s.dependency "others"

end
