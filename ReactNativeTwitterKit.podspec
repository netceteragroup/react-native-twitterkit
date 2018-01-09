
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
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/netceteragroup/react-native-twitterkit", :branch => "master" }
  s.source_files  = "ios/*.{h,m}"
  s.public_header_files = "ios/*.h"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "Fabric"
  s.dependency "TwitterCore", "3.0.3"
  s.dependency "TwitterKit", "3.2.2"

  # For now, we are going with static libararies (default); the following
  # stays commented out, in case we need it

  # Force the static framework to be linked into this pod
  # See https://github.com/CocoaPods/CocoaPods/issues/2926

  # s.frameworks =
  #   'Fabric',
  #   'TwitterCore',
  #   'TwitterKit',
  #   "Accounts",
  #   "CoreData",
  #   "CoreGraphics",
  #   "Foundation",
  #   "Security",
  #   "Social",
  #   "UIKit",
  #   "CoreText",
  #   "QuartzCore",
  #   "CoreMedia",
  #   "AVFoundation",
  #   "SafariServices"
  #
  # s.pod_target_xcconfig = {
  # 	'FRAMEWORK_SEARCH_PATHS' => '$(inherited) "$(PODS_ROOT)/Fabric/iOS" "$(PODS_ROOT)/TwitterCore/iOS" "$(PODS_ROOT)/TwitterKit/iOS"'
  # }

end
