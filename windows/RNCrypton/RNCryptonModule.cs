using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Crypton.RNCrypton
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNCryptonModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNCryptonModule"/>.
        /// </summary>
        internal RNCryptonModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNCrypton";
            }
        }
    }
}
