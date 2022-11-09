var merge = require('webpack-merge').merge;
var generated = require('./scalajs.webpack.config');

var local = {
    devServer: {
        historyApiFallback: true
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(ttf|eot|woff|png|glb|svg)$/,
                use: 'file-loader'
            },
            {
                test: /\.(eot)$/,
                use: 'url-loader'
            },
            {
                test: /\.(js|mjs)$/,
                resolve: {
                    fullySpecified: false
                }
            }
        ]
    }
};

module.exports = merge(generated, local);
