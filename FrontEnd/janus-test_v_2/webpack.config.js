const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const Dotenv = require('dotenv-webpack');
const fs = require('fs');

module.exports = {
  name: 'janus-test',
  mode: 'development',
  devtool: 'eval',

  module: {
    rules: [
      {
        test: /\.jsx?/,
        exclude: /node_modules|\.json$/,  // Exclude JSON files here
        loader: 'babel-loader',
        options: {
          presets: ['@babel/preset-env', '@babel/preset-react']
        }
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader", "postcss-loader"],
      },
      {
        test: require.resolve('janus-gateway'),
        loader: 'exports-loader',
        options: {
          exports: 'Janus',
        },
      },
    ],
  },
  entry: {
    app: ['./src/client.jsx', './src/app.css'],
  },
  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, 'dist')
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './index.html'
    }),
    // new MiniCssExtractPlugin({
    //   filename: 'app.css'
    // }),
    new webpack.ProvidePlugin({
      adapter: ['webrtc-adapter', 'default']
    }),
    new Dotenv(),
  ],
  devServer: {
    server: {
      type: 'https',
      options: {
        key: fs.readFileSync('./keys/key.pem'),
        cert: fs.readFileSync('./keys/cert.pem'),
      },
    },
    static: {
      directory: path.resolve(__dirname, 'dist')
    },
    port: 3030,
  },
  resolve: {
    extensions: [".jsx", ".js"],
  },
};